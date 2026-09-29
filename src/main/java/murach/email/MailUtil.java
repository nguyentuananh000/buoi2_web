package murach.email;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {

    public static void sendMail(String to, String subject, String body) {
        // THAY BẰNG EMAIL VÀ MẬT KHẨU ỨNG DỤNG CỦA BẠN VÀO ĐÂY
        final String FROM_EMAIL = "ta06022006@gmail.com"; 
        final String APP_PASSWORD = "ygdc tteu wnwy gxvg"; // 16 ký tự vừa copy ở Bước 1

        // 1. Cấu hình kết nối tới server của Gmail
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // 2. Xác thực tài khoản đăng nhập
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        try {
            // 3. Soạn nội dung tin nhắn
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            
            // Dùng setContent để hỗ trợ gõ tiếng Việt có dấu
            message.setContent(body, "text/html; charset=UTF-8");

            // 4. Gửi tin nhắn
            Transport.send(message);
            System.out.println("Gửi mail thành công đến: " + to);

        } catch (MessagingException e) {
            System.out.println("Lỗi gửi mail: " + e.toString());
        }
    }
}