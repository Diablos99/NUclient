package NU_Sing_WX;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;;

public class GroupFileView extends JFrame {
	
	protected static Connection connect; 

    private int width = 400;
    private int height = 600;

    private JLabel groupLabel;
    private JButton uploadButton;
    private JLabel flushLabel;
    private String fileIconPath = "�ļ��ͻ���/resource/file-min.png";
    private JScrollPane jScrollPane;
    private JPanel staffPanel;      //��JSpanel�ϵ�panel

    private Socket client_socket;
   protected static PrintStream client_out;
    private BufferedReader client_in;
    private String ip = "172.20.10.2";
    private int port = 5203;

    private File currentUpploadFile;
    protected static String downloadSavePath;
    private int Y = 0;

    public static boolean mkDirectory(String path) {
		File file = null;
		try {
			file = new File(path);
			if (!file.exists()) {
				return file.mkdirs();
			}
			else{
				return false;
			}
		} catch (Exception e) {
		} finally {
			file = null;
		}
		return false;
	}

    public GroupFileView() {
        //1-��ʼ��
        initVariable();
        //2-���ӷ�����
        connectServer();
        //3-ע�����
        registerListener();
		//4-��ʼ����������
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setTitle("Ⱥ�ļ�");
        this.setLocationRelativeTo(null);//���ھ�����ʾ
        this.setResizable(false);
        this.setVisible(false);
    }




    private void initVariable() {
        jScrollPane = new JScrollPane();
        this.getContentPane().add(jScrollPane);
        
        staffPanel = new JPanel();
        ///staffPanel.setLayout(new BoxLayout(staffPanel,BoxLayout.Y_AXIS));
        staffPanel.setLayout(null);
        staffPanel.setOpaque(false);
        staffPanel.setPreferredSize(new Dimension(width,height));

        jScrollPane.setViewportView(staffPanel);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//����ˮƽ����������
        jScrollPane.getViewport().setOpaque(false);  //����͸��
        jScrollPane.setOpaque(false);  //����͸��
        staffPanel.setVisible(true);

        renderTop();
    }


    /**
     *      ����������¶�ȡȺ�ļ��б�
     */
    private void loadGroupFile() {
            client_out.println("@action=loadFileList");
    }


    /**
     *   ��Ⱦ�������
     */
    private void renderTop(){
        staffPanel.removeAll();
        Y = 0;
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,3,3,10));
        this.groupLabel = new JLabel("\t\t\t\t\tȺ�ļ��б� ");
        this.uploadButton = new JButton("�ϴ��ļ� ");
        flushLabel = new JLabel(new ImageIcon("�����ҿͻ���/resource/flush.png"));
        panel.add(groupLabel);
        panel.add(uploadButton);
        panel.add(flushLabel);
        panel.setVisible(true);

        panel.setBounds(2,Y,width,30);
        this.staffPanel.add(panel);
        Y += 30;
    }
    /**
         ��Ⱦ�ļ��б�
     */
    public  void addToFileList(String filename){
        JLabel fileicon = new JLabel(new ImageIcon(fileIconPath));
        JButton downloadBtn = new JButton("����");
        JLabel fileNameLab = new JLabel(filename);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,3,0,0));
        panel.add(fileicon);
        panel.add(fileNameLab);
        panel.add(downloadBtn);
        //panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        panel.setBounds(2,Y,width,30);
        this.staffPanel.add(panel);
        Y+=30;

        panel.addMouseListener(new MouseAdapter() {
            //�������ʱ
            public void mouseEntered(MouseEvent e) { // ����ƶ���������¼�
                panel.setBackground(Color.orange);
                panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // ������ƶ���
            }
            public void mouseExited(MouseEvent e) { // ����뿪���¼�
                panel.setBackground(Color.white);
            }

        });

        //�ļ�����
        downloadBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                 //1-ѡ�����ر����λ��
                JFileChooser f = new JFileChooser(); // �����ļ�
                f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                f.showOpenDialog(null);
                File file = f.getSelectedFile();

                if(file != null){
                    downloadSavePath  = file.getPath();
                    //���������������
                    client_out.println("@action=Download["+filename+":null:null]");
                }
            }
        });
       
        
        
    }
    
    /**
     *   ע�����
     */
    private void registerListener() {
        //�ϴ��ļ�    ��Ϣ��ʽ: @action=Upload["fileName":"fileSize":result]
        this.uploadButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JFileChooser f = new JFileChooser(); // �����ļ�
                f.showOpenDialog(null);
                currentUpploadFile = f.getSelectedFile();
                if(currentUpploadFile != null)
                client_out.println("@action=Upload["+currentUpploadFile.getName()+":"+currentUpploadFile.length()+":null]");

            }
        });

        //ˢ���ļ��б�ť
        flushLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                loadGroupFile();
            }
            //�������ʱ
            public void mouseEntered(MouseEvent e) { // ����ƶ���������¼�
                flushLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // ������ƶ���
            }
        });
    }

    /**
     *  ���ӷ�����
     */
    private void connectServer(){
        //���ӷ�����
        try {
            //��ʼ��
            client_socket = new Socket(ip,port);
            client_out = new PrintStream(client_socket.getOutputStream(),true);
            client_in = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));

            //��ȡ�ļ��б�
            client_out.println("@action=loadFileList");

            //�����̼߳�����������Ϣ
            new ClientThread().start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     *    ������������Ϣ
     */
    class ClientThread extends Thread{
        public void run() {
            try {
                String fromServer_data;
                int flag = 0;

                while((fromServer_data=client_in.readLine()) != null){
                        //��ȡȺ�ļ��б�
                        if(flag++ == 0){
                            if (fromServer_data.startsWith("@action=GroupFileList")){
                                String[] fileList = ParseDataUtil.getFileList(fromServer_data);
                                for (String filename : fileList) {
                                    addToFileList(filename);
                                }
                            }
                            continue;
                        }
                        if(fromServer_data.startsWith("@action=GroupFileList")){
                            //������Ⱦ�������
                            renderTop();

                            //ע�����
                            registerListener();

                            //��Ⱦ�ļ����
                            String[] fileList = ParseDataUtil.getFileList(fromServer_data);
                            for (String filename : fileList) {
                                addToFileList(filename);
                            }

                        }

                        //�ļ��ϴ�
                        if (fromServer_data.startsWith("@action=Upload")){
                            String res = ParseDataUtil.getUploadResult(fromServer_data);
                            if("NO".equals(res)){
                                JOptionPane.showMessageDialog(null,"�ļ��Ѵ���!");
                            }else if ("YES".equals(res)){
                                //��ʼ�ϴ�
                                if(currentUpploadFile != null){
                                    //�������̴߳����ļ�
                                    new HandelFileThread(1).start();
                                }

                            }else if ("�ϴ����".equals(res)){
                                JOptionPane.showMessageDialog(null,res);
                                loadGroupFile();
                            }
                        }

                        //�ļ�����
                        if(fromServer_data.startsWith("@action=Download")){
                            String res = ParseDataUtil.getDownResult(fromServer_data);
                            if(res.equals("�ļ�������")){
                                JOptionPane.showMessageDialog(null,"���ļ�������404");
                            }else {
                                String downFileName = ParseDataUtil.getDownFileName(fromServer_data);
                                String downFileSize = ParseDataUtil.getDownFileSize(fromServer_data);
                                //�������̴߳����ļ�
                                new HandelFileThread(0,downFileName,downFileSize).start();
                            }
                        }
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**----------------------------------------------------------------------------------
         *     �ļ������߳�
         */
        class HandelFileThread extends Thread{
            private int mode;  //�ļ�����ģʽ  1-�ϴ�  2-����
            private String filename;
            private Long fileSize;

            public HandelFileThread(int mode) {
                this.mode = mode;
            }
            public HandelFileThread(int mode,String filename,String fileSize){
                this.mode = mode;
                this.filename = filename;
                this.fileSize = Long.parseLong(fileSize);
            }

            public void run() {
                try {
                    //�ϴ��ļ�ģʽ
                    if(this.mode == 1){
                        Socket socket = new Socket(ip,8888);
                        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(currentUpploadFile));
                        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());

                        int len;
                        int i = 0;
                        double sum = 0;
                        byte[] arr = new byte[8192];
                        String schedule;

                        System.out.println("��ʼ�ϴ�--�ļ���СΪ��"+currentUpploadFile.length());

                        while((len = bis.read(arr)) != -1){
                            bos.write(arr,0,len);
                            bos.flush();
                            sum += len;
                            if (i++ %100 == 0){
                                schedule = "�ϴ�����:"+100*sum/currentUpploadFile.length()+"%";
                                System.out.println(schedule);
                            }
                        }
                        //�ϴ����
                        socket.shutdownOutput();
                        System.out.println("�ϴ�����:100%");
                    }

                    //�����ļ�ģʽ
                    if(this.mode == 0){
                        Socket socket = new Socket(ip,8888);
                        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(downloadSavePath+"/"+filename));
                        
                        int len;
                        byte[] arr =new byte[8192];
                        double sumDown = 0;
                        int i = 0;
                        String serverString;
                        System.out.println("�ͻ��˿�ʼ���� ");                      
                        while ((len = bis.read(arr)) != -1){
                            sumDown += len;
                            if(i++%100 == 0)
                                System.out.println("���ؽ���Ϊ��"+100*sumDown/fileSize+"%");
                            serverString = new String(arr,0,len,"GB18030");
                            System.out.println("server:" + serverString);
                            bos.write(arr,0,len);
                            bos.flush();
                        }

                        bos.close();
                        bis.close();
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	//��������
    public static void main(String[] args) throws Exception {
      
    }
}

