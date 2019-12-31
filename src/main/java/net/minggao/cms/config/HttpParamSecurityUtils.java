package net.minggao.cms.config;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.errors.EncodingException;

import java.io.IOException;

/**
 * 所有从页面获取数据都需要进行安全处理
 * 本工具类就是对页面获取的数据进行安全处理
 */
public class HttpParamSecurityUtils {

    public static String encodeForHTMLAttribute(String input){
        return ESAPI.encoder().encodeForHTMLAttribute(input);
    }

    /**
     * 统一对参数进行处理
     * @param input
     * @return
     */
    public static String canonicalize(String input){
        return ESAPI.encoder().canonicalize(input);
    }

    /**
     * 对参数进行html编码的转换（转义），保证在页面中显示不会出现错误
     * @param input
     * @return
     */
    public static String encodeForHTML(String input){
        return ESAPI.encoder().encodeForHTML(input);
    }

    /**
     * 针对javascript保留字符进行转义处理
     * @param input
     * @return
     */
    public static String encodeForJavaScript(String input){
        return ESAPI.encoder().encodeForJavaScript(input);
    }

    /**
     * 对于URL类型的字符串，进行转义
     * @param input
     * @return
     */
    public static String encodeForURL(String input)throws Exception{
        return ESAPI.encoder().encodeForURL(input);
    }


    /**
     * 对于css相关的字符进行转义
     * @param input
     * @return
     */
    public static String encodeForCSS(String input){
        return ESAPI.encoder().encodeForCSS(input);
    }

    /**
     * 对已经转义过的，给取消转义
     * @param input
     * @return
     */
    public static String decodeForHTML(String input){
        return ESAPI.encoder().decodeForHTML(input);
    }


    /**
     * 对于vbscript类型的字符进行转义
     */
    public static  String encodeForVBScript(String input){
        return ESAPI.encoder().encodeForVBScript(input);
    }


    /**
     * Encode input for use in a SQL query, according to the selected codec
     * (appropriate codecs include the MySQLCodec and OracleCodec).
     *
     * This method is not recommended. The use of the PreparedStatement
     * interface is the preferred approach. However, if for some reason
     * this is impossible, then this method is provided as a weaker
     * alternative.
     *
     * The best approach is to make sure any single-quotes are double-quoted.
     * Another possible approach is to use the {escape} syntax described in the
     * JDBC specification in section 1.5.6.
     *
     * However, this syntax does not work with all drivers, and requires
     * modification of all queries.
     *
     * @see <a href="http://java.sun.com/j2se/1.4.2/docs/guide/jdbc/getstart/statement.html">JDBC Specification</a>
     *
     * @param codec
     * 		a Codec that declares which database 'input' is being encoded for (ie. MySQL, Oracle, etc.)
     * @param input
     * 		the text to encode for SQL
     *
     * @return input encoded for use in SQL
     */
    public static  String encodeForSQL(Codec codec, String input){
        return ESAPI.encoder().encodeForSQL(codec,input);
    }

    /**
     * Encode for an operating system command shell according to the selected codec (appropriate codecs include the WindowsCodec and UnixCodec).
     *
     * Please note the following recommendations before choosing to use this method:
     *
     * 1)      It is strongly recommended that applications avoid making direct OS system calls if possible as such calls are not portable, and they are potentially unsafe. Please use language provided features if at all possible, rather than native OS calls to implement the desired feature.
     * 2)      If an OS call cannot be avoided, then it is recommended that the program to be invoked be invoked directly (e.g., System.exec("nameofcommand" + "parameterstocommand");) as this avoids the use of the command shell. The "parameterstocommand" should of course be validated before passing them to the OS command.
     * 3)      If you must use this method, then we recommend validating all user supplied input passed to the command shell as well, in addition to using this method in order to make the command shell invocation safe.
     *
     * An example use of this method would be: System.exec("dir " + ESAPI.encodeForOS(WindowsCodec, "parameter(s)tocommandwithuserinput");
     *
     * @param codec
     *      a Codec that declares which operating system 'input' is being encoded for (ie. Windows, Unix, etc.)
     * @param input
     *      the text to encode for the command shell
     *
     * @return input encoded for use in command shell
     */
    public static  String encodeForOS(Codec codec, String input){
        return ESAPI.encoder().encodeForOS(codec,input);
    }

    /**
     * Encode data for use in LDAP queries.
     *
     * @param input
     * 		the text to encode for LDAP
     *
     * @return input encoded for use in LDAP
     */
    public static  String encodeForLDAP(String input){
        return ESAPI.encoder().encodeForLDAP(input);
    }

    /**
     * Encode data for use in an LDAP distinguished name.
     *
     *  @param input
     *  		the text to encode for an LDAP distinguished name
     *
     *  @return input encoded for use in an LDAP distinguished name
     */
    public static  String encodeForDN(String input){
        return ESAPI.encoder().encodeForDN(input);
    }

    /**
     * Encode data for use in an XPath query.
     *
     * NB: The reference implementation encodes almost everything and may over-encode.
     *
     * The difficulty with XPath encoding is that XPath has no built in mechanism for escaping
     * characters. It is possible to use XQuery in a parameterized way to
     * prevent injection.
     *
     * For more information, refer to <a
     * href="http://www.ibm.com/developerworks/xml/library/x-xpathinjection.html">this
     * article</a> which specifies the following list of characters as the most
     * dangerous: ^&"*';<>(). <a
     * href="http://www.packetstormsecurity.org/papers/bypass/Blind_XPath_Injection_20040518.pdf">This
     * paper</a> suggests disallowing ' and " in queries.
     *
     * @see <a href="http://www.ibm.com/developerworks/xml/library/x-xpathinjection.html">XPath Injection [ibm.com]</a>
     * @see <a href="http://www.packetstormsecurity.org/papers/bypass/Blind_XPath_Injection_20040518.pdf">Blind XPath Injection [packetstormsecurity.org]</a>
     *
     * @param input
     *      the text to encode for XPath
     * @return
     * 		input encoded for use in XPath
     */
    public static  String encodeForXPath(String input){
        return ESAPI.encoder().encodeForXPath(input);
    }

    /**
     * Encode data for use in an XML element. The implementation should follow the <a
     * href="http://www.w3schools.com/xml/xml_encoding.asp">XML Encoding
     * Standard</a> from the W3C.
     * <p>
     * The use of a real XML parser is strongly encouraged. However, in the
     * hopefully rare case that you need to make sure that data is safe for
     * inclusion in an XML document and cannot use a parse, this method provides
     * a safe mechanism to do so.
     *
     * @see <a href="http://www.w3schools.com/xml/xml_encoding.asp">XML Encoding Standard</a>
     *
     * @param input
     * 			the text to encode for XML
     *
     * @return
     *			input encoded for use in XML
     */
    public static  String encodeForXML(String input){
        return ESAPI.encoder().encodeForXML(input);
    }

    /**
     * Encode data for use in an XML attribute. The implementation should follow
     * the <a href="http://www.w3schools.com/xml/xml_encoding.asp">XML Encoding
     * Standard</a> from the W3C.
     * <p>
     * The use of a real XML parser is highly encouraged. However, in the
     * hopefully rare case that you need to make sure that data is safe for
     * inclusion in an XML document and cannot use a parse, this method provides
     * a safe mechanism to do so.
     *
     * @see <a href="http://www.w3schools.com/xml/xml_encoding.asp">XML Encoding Standard</a>
     *
     * @param input
     * 			the text to encode for use as an XML attribute
     *
     * @return
     * 			input encoded for use in an XML attribute
     */
    public static  String encodeForXMLAttribute(String input){
        return ESAPI.encoder().encodeForXMLAttribute(input);

    }


    /**
     * Decode from URL. Implementations should first canonicalize and
     * detect any double-encoding. If this check passes, then the data is decoded using URL
     * decoding.
     *
     * @param input
     * 		the text to decode from an encoded URL
     *
     * @return
     * 		the decoded URL value
     *
     * @throws EncodingException
     * 		if decoding fails
     */
    public static  String decodeFromURL(String input) throws EncodingException{
        return ESAPI.encoder().decodeFromURL(input);
    }

    /**
     * Encode for Base64.
     *
     * @param input
     * 		the text to encode for Base64
     * @param wrap
     * 		the encoder will wrap lines every 64 characters of output
     *
     * @return input encoded for Base64
     */
    public static  String encodeForBase64(byte[] input, boolean wrap){
        return ESAPI.encoder().encodeForBase64(input,wrap);
    }

    /**
     * Decode data encoded with BASE-64 encoding.
     *
     * @param input
     * 		the Base64 text to decode
     *
     * @return input
     * 		decoded from Base64
     *
     * @throws IOException
     */
    public static  byte[] decodeFromBase64(String input) throws IOException{
        return ESAPI.encoder().decodeFromBase64(input);
    }
}
