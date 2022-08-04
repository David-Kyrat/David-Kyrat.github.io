import $ivy.`net.sourceforge.htmlcleaner:htmlcleaner:2.26`
import $ivy.`net.sourceforge.htmlunit:htmlunit:2.62.0`
import java.nio.file.{Files, Path, StandardOpenOption, OpenOption}
import java.nio.file.Files.write
import java.nio.file.Path._
import java.nio.charset.StandardCharsets.UTF_8
import com.gargoylesoftware.htmlunit
import com.gargoylesoftware.htmlunit._
import com.gargoylesoftware.htmlunit.html.HtmlPage
//WebClient
import com.gargoylesoftware.htmlunit.html.DomElement
import com.gargoylesoftware.htmlunit.html.DomElement._
import org.htmlcleaner.HtmlCleaner
import org.htmlcleaner.HtmlCleaner._
import org.htmlcleaner._
import org.htmlcleaner.CleanerProperties
import org.htmlcleaner.CleanerProperties._ 
import org.htmlcleaner.DomSerializer
import org.htmlcleaner.DomSerializer._
import java.io.ByteArrayOutputStream
import scala.io.Source
import java.io._
import java.net.URL

//asXml
//import java.nio.file.Files._

def getHtml(): String = {
	val htmlFile = "C:/Users/noahm/OneDrive/Prog-Projects/pages-repo/temp.html"
	Source.fromFile(htmlFile)(UTF_8).getLines().mkString("\n")
}

def renderHtml(htmlSrc: String="") = {
	val cleaner = new HtmlCleaner(); val out = new ByteArrayOutputStream(); val webClient = new WebClient(); val fileName = "temp.html";
	try {
		val htmlPath = Path.of("C:/Users/noahm/OneDrive/Prog-Projects/pages-repo",fileName)
		/*val wr = new PrintWriter(new OutputStreamWriter(new FileOutputStream(htmlPath.toFile), UTF_8))
		wr.println(htmlSrc); wr.close()
		htmlPath.toFile().createNewFile()*/
		val page: HtmlPage = webClient.getPage[HtmlPage](htmlPath.toUri.toURL.toString);
		val pageAsXml = page.asXml();
		println("Before tag node -->\n\t"+pageAsXml);
		val node = cleaner.clean(pageAsXml);
		val props = new CleanerProperties()
		val domSerializer = new DomSerializer(props);
		val document = domSerializer.createDOM(node);
		new PrettyXmlSerializer(props).writeToStream(node, out);
		out.flush();
		out.close();
	}
	catch  {
		case e: Exception => e.printStackTrace()
		case _: Throwable => ""
	}
	finally {
		/*webClient.close();
		if(webClient!=null && webClient.getCookieManager()!=null) {
			webClient.getCookieManager().clearCookies();
		}*/
	}
	while(true) {}
}

renderHtml()


