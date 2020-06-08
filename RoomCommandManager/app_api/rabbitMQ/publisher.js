var amqp = require('amqplib/callback_api');

var exchangeName = 'room_command_exchange';

// var key = 'data_retriever.example';

var channel;

amqp.connect('amqp://localhost', function(connect_error, connection) {
  if (connect_error) {
    throw connect_error;
  }
  
  connection.createChannel(function(channel_error, ch) {
    if (channel_error) {
      throw channel_error;
    }

    channel = ch;

    channel.assertExchange(exchangeName, 'topic', {
      durable: true
    });

  });
});

module.exports.publishActivity = function(msg, key) { 
  channel.publish(exchangeName, key, Buffer.from(JSON.stringify(msg)));
  console.log(" [x] Attività inviata a %s :", key);
  console.log(msg)
};