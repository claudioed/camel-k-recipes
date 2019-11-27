import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;

public class NatsFilter extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    from("nats://10.87.5.120:4222?topic=payments-processed")
        .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
        .transform().simple(" ${body.data}")
        .log(" ${body}")
        .to("direct:choice");

    from("direct:choice")
    .log(" ${body}")
    .choice()
      .when()
       .jsonpath("$.customer[?(@.country == 'Brazil')]",true)
         .log("Found Brazil")
         .to("nats://10.87.5.120:4222?topic=payments-processed-br")
      .when()
       .jsonpath("$.customer[?(@.country == 'Canada')]",true)
        .log("Found Canada")
        .to("nats://10.87.5.120:4222?topic=payments-processed-canada")
    .endChoice()
     .otherwise()
      .log("=====")
     .to("stream:out");
  }
}