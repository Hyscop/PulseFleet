import { useEffect, useState } from "react";
import mqtt from "mqtt";

const MQTT_URL = "ws://localhost:8883/mqtt";

export function useMqtt(topic: string, onMessage?: (payload: string) => void) {
  const [status, setStatus] = useState<"Connected" | "Disconnected">(
    "Disconnected",
  );

  useEffect(() => {
    const client = mqtt.connect(MQTT_URL, {
      clientId: `dashboard_${Math.random().toString(16).slice(2)}`,
      keepalive: 60,
    });

    client.on("connect", () => {
      console.log("MQTT Connected");
      setStatus("Connected");
      client.subscribe(topic, (err) => {
        if (err) console.error("Subscribe error:", err);
      });
    });

    client.on("message", (receivedTopic, payload) => {
      if (onMessage) {
        onMessage(payload.toString());
      }
    });

    return () => {
      if (client.connected) {
        client.end();
      }
    };
  }, [topic, onMessage]);

  return { status };
}
