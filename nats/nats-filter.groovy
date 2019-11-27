from("nats://10.87.13.169:4222?topic=payments-processed")
        .choice()
        .when().jsonpath("$.customer.country == Brazil")
        .log('BRAZIL ${body.data}')
        .when().jsonpath("$.customer.country == Canada")
        .log('CANADA ${body.data}')
        .otherwise()
        .log('=====')