import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Server extends JFrame
{

    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    //Declare Components
    private JLabel heading = new JLabel("Server Area");
    private JTextArea messageArea = new JTextArea();
    private JTextField messageInput = new JTextField();
    private Font font = new Font("Arial", Font.PLAIN, 20);


    public Server()
    {
        try {
            server = new ServerSocket(7778);
            System.out.println("Server is ready to accept connection");
            System.out.println("waiting....");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            createGUI();
            handleEvents();

            startReading();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleEvents() 
    {
        messageInput.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
                
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
               
               // System.out.println("key released " + e.getKeyCode());
                if(e.getKeyCode() == 10) {
                    //System.out.println("You have pressed enter button");
                    String contentToSend = messageInput.getText();
                    messageArea.append("Me :" + contentToSend + "\n");
                    out.println(contentToSend);
                    out.flush();
                    messageInput.setText("");
                }
                
            }

        });

    }

    private void createGUI()
    {
        //gui code

        this.setTitle("Network Messenger");
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //coding for Components
        heading.setFont(font);
        messageArea.setFont(font);
        messageInput.setFont(font);

        heading.setIcon(new ImageIcon("clogo.png"));
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        messageArea.setEditable(false);
        

        //setting frame's layout
        this.setLayout(new BorderLayout());

        //adding componenets to frame
        this.add(heading, BorderLayout.NORTH);
        JScrollPane jScrollPane = new JScrollPane(messageArea);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.add(messageInput, BorderLayout.SOUTH);



        this.setVisible(true);
    }
    
    public void startReading()
    {
        // thread - read data from the Client
        Runnable r1=()->{

            System.out.println("Reader started..");

            try{
            while(true)
            {
                
                    String msg = br.readLine();
                    if(msg.equals("exit"))
                    {
                        System.out.println("Client has terminated the chat");
                        JOptionPane.showMessageDialog(this, "Client Terminated the chat");
                        messageInput.setEnabled(false);
                        socket.close();
                        break;
                    }       
                    messageArea.append("Client : " + msg + "\n");
            }
            System.out.println("Connection is closed\n");
        }catch(Exception e) {
            System.out.println("Connection is closed\n");
         }
        };
        new Thread(r1).start();
    }

    public static void main(String[] args)
    {
        System.out.println("This is the server");
        new Server();
    }
}







