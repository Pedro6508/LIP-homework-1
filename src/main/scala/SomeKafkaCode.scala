import org.apache.kafka.clients.consumer.KafkaConsumer
import org.slf4j.Logger

import scala.jdk.CollectionConverters

object SomeKafkaCode {
  private def slf4jLogger: Logger = {
    import org.slf4j.LoggerFactory
    LoggerFactory.getLogger("SomeKafkaCode")
  }

  def defaultProducer = {
    val scalaMap = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "acks" -> "all",
      "retries" -> "0",
      "batch.size" -> "16384",
      "linger.ms" -> "1",
      "buffer.memory" -> "33554432",
      "key.serializer" -> "org.apache.kafka.common.serialization.StringSerializer",
      "value.serializer" -> "org.apache.kafka.common.serialization.StringSerializer"
    )
    val javaMap = CollectionConverters.MapHasAsJava(scalaMap).asJava
    val producer = new org.apache.kafka.clients.producer.KafkaProducer[String, String](javaMap)
    producer
  }

  def defaultConsumer = {
    val scalaMap = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "group.id" -> "test",
      "enable.auto.commit" -> "true",
      "auto.commit.interval.ms" -> "1000",
      "session.timeout.ms" -> "30000",
      "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer"
    )
    val javaMap = CollectionConverters.MapHasAsJava(scalaMap).asJava
    val consumer = new KafkaConsumer[String, String](javaMap)
    consumer
  }

  def main(args: Array[String]): Unit = {
    val consumer = defaultConsumer
    val producer = defaultProducer
    producer.send(new org.apache.kafka.clients.producer.ProducerRecord[String, String]("test", "key", "value"))
    consumer.subscribe(java.util.Collections.singletonList("test"))
    slf4jLogger.info("Subscribed to topic " + "test")
  }
}
