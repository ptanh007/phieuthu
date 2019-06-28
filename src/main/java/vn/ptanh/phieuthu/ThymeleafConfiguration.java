package vn.ptanh.phieuthu;

import org.springframework.context.annotation.Bean;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * @author tuanhpham.
 */
public class ThymeleafConfiguration {
    @Bean
    public ClassLoaderTemplateResolver emailTemplateResolver(){
        ClassLoaderTemplateResolver phieuThuTemplate=new ClassLoaderTemplateResolver();
        phieuThuTemplate.setPrefix("templates/");
        phieuThuTemplate.setTemplateMode("HTML5");
        phieuThuTemplate.setSuffix(".html");
        phieuThuTemplate.setTemplateMode("XHTML");
        phieuThuTemplate.setCharacterEncoding("UTF-8");
        phieuThuTemplate.setOrder(1);
        return phieuThuTemplate;
    }
}
