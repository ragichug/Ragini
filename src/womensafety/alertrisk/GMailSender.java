package womensafety.alertrisk;



import javax.activation.CommandMap;
import javax.activation.DataHandler;   
import javax.activation.DataSource;   
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Message;   
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;   
import javax.mail.Session;   
import javax.mail.Transport;   
import javax.mail.internet.InternetAddress;   
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;   
import javax.mail.internet.MimeMultipart;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.ByteArrayInputStream;   
import java.io.File;
import java.io.IOException;   
import java.io.InputStream;   
import java.io.OutputStream;   
import java.security.Security;   
import java.util.Date;
import java.util.Properties;   


public class GMailSender extends javax.mail.Authenticator {
    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;
   
    Bitmap userImage;
   
    byte [] img;
    static {
        Security.addProvider(new org.apache.harmony.xnet.provider.jsse.JSSEProvider());
    }
 
    public GMailSender(String user, String password) {
        this.user = user;
        this.password = password;
 
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");
 
        session = Session.getDefaultInstance(props, this);
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap(); 
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html"); 
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml"); 
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain"); 
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed"); 
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822"); 
        CommandMap.setDefaultCommandMap(mc);
       
    }
 
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }
   

	    public void addAttachment(String filename,String subject,MimeMessage message) throws Exception { 
	    	  Multipart multipart = new MimeMultipart(); 
	        BodyPart messageBodyPart = new MimeBodyPart(); 
	        DataSource source = new FileDataSource(filename); 
	        messageBodyPart.setDataHandler(new DataHandler(source)); 
	        messageBodyPart.setFileName(filename); 
	        multipart.addBodyPart(messageBodyPart);

	        BodyPart messageBodyPart2 = new MimeBodyPart(); 
	        messageBodyPart2.setText(subject); 

	        multipart.addBodyPart(messageBodyPart2); 
	  



	    message.setContent(multipart);
	    Transport.send(message);
	    }
    public synchronized void sendMail(String subject, String body, String sender, String recipients) throws Exception {
    	
    	
    	File destination=new File(Environment.getExternalStorageDirectory(),"myPicture.jpg");
    	String s=destination.getAbsolutePath();
        MimeMessage message = new MimeMessage(session);
        DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
        message.setSender(new InternetAddress(sender));
        message.setSubject(subject);
        message.setSentDate(new Date());
        message.setDataHandler(handler);
        
     
       
        if (recipients.indexOf(',') > 0)
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
        else
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
        message.saveChanges();
        addAttachment(s,body,message);
        
       
    }
 
    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;
 
        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }
 
        public ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }
 
        public void setType(String type) {
            this.type = type;
        }
 
        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }
 
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }
 
        public String getName() {
            return "ByteArrayDataSource";
        }
 
        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }
}