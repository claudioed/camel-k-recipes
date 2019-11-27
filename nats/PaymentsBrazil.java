import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;

public class PaymentsBrazil extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    from("nats://10.87.5.120:4222?topic=payments-processed-br")
        .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
        .transform().simple(" ${body.data}")
        .log(" ${body}")
        .to("direct:br");

    from("direct:br")
    .log(" Brazil Payment ${body}")
    .to("stream:out");
  }
}