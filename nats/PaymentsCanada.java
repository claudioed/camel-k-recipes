import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;

public class PaymentsCanada extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    from("nats://10.87.5.120:4222?topic=payments-processed-canada")
        .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
        .transform().simple(" ${body.data}")
        .log(" ${body}")
        .to("direct:canada");

    from("direct:canada")
    .log(" Canada Payment ${body}")
    .to("stream:out");
  }
}