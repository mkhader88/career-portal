const { Kafka, logLevel } = require("kafkajs");
const kafka = new Kafka({
    clientId: process.env.KAFKA_CLIENT_ID,
    brokers: [process.env.KAFKA_BROKER_URL],
    ssl: false,
    connectionTimeout: 3000,
    requestTimeout: 25000,
    logLevel: logLevel.ERROR,
    retry: {
        initialRetryTime: 100,
        retries: 8
    }
})

const producer = kafka.producer()
const Producer = async(job_seeker) => {
    console.log("creating an email event to email service: " + job_seeker.email);
    await producer.connect()
    await producer.send({
        topic: process.env.EMAIL_TOPIC,
        messages: [
            { value: JSON.stringify({ to: job_seeker.email, subject: "New Position Available", text: "Hello Applicant,\n" +
                        "\n" +
                        "Good news, there's a new position available which matches your skills. Please login to your account and start applying." }) },
        ],
    });
}

module.exports = Producer