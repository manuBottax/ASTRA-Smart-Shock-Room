var amqp = require('amqplib/callback_api');

var exchangeName = 'command_exchange';

var key = 'data_retriever.example';

var channel;

amqp.connect('amqp://localhost', function(error0, connection) {
  if (error0) {
    throw error0;
  }
  connection.createChannel(function(error1, ch) {
    if (error1) {
      throw error1;
    }

    channel = ch;

    channel.assertExchange(exchangeName, 'topic', {
      durable: false
    });

  });
});

module.exports.sendMessage = function(msg) { 
  channel.publish(exchangeName, key, Buffer.from(msg));
  console.log(" [x] Sent %s:'%s'", key, msg);
};