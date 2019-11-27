import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;

public class FromPaymentsBrazil extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    from("knative:channel/brazil")
        .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
        .transform().simple(" ${body.data}")
        .log(" ${body}")
        .to("direct:br");

    from("direct:br")
    .log(" Brazil Payment ${body}")
    .to("stream:out");
  }
}