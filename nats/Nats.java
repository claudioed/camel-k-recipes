import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;

public class Nats extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    from("nats://10.87.5.120:4222?topic=payments-processed")
        .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
        .transform().simple(" ${body.data}")
        .log(" ${body}")
        .to("direct:choice");

    from("direct:choice")
    .setHeader("Content-Type", constant("application/json"))
    .setHeader("Ce-specversion", constant("1.0"))
    .log(" ${body}")
    .to("mock:result")
    .log("Message Sent");
  }
}