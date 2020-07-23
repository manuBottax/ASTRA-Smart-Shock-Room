var amqp = require('amqplib/callback_api');

var exchangeName = 'room_command_exchange';

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
  console.log(" [RCM] Command Send to %s :", key);
  console.log(msg)
};