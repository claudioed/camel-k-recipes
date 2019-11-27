from('knative:channel/messages')
        .log('sending ${body} to words channel')
        .to('knative:channel/words')