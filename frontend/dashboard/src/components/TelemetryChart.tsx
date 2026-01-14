import { useEffect, useState } from "react";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from "recharts";
import type { Telemetry } from "../types/Telemetry";
import { fetchTelemetry } from "../api/telemetryApi";

interface Props {
  deviceId: string;
}

export function TelemetryChart({ deviceId }: Props) {
  const [data, setData] = useState<Telemetry[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    let cancelled = false;
    fetchTelemetry(deviceId)
      .then((telemetry) => {
        if (!cancelled) setData(telemetry);
      })
      .catch((err) => {
        if (!cancelled) {
          setError(err.message);
        }
      })
      .finally(() => {
        if (!cancelled) {
          setLoading(false);
        }
      });

    return () => {
      cancelled = true;
    };
  }, [deviceId]);

  if (loading) {
    return <div className="p-4">Loading telemetry...</div>;
  }
  if (error) {
    return <div className="p-4 text-red-500">Error: {error}</div>;
  }
  if (data.length === 0) {
    return (
      <div className="p-4 text-gray-500">No telemetry data availiable</div>
    );
  }

  const chartData = data.map((t) => ({
    time: new Date(t.receivedAt).toLocaleTimeString(),
    temperature: t.temperature,
    battery: t.battery,
  }));

  return (
    <div className="bg-white rounded-lg shadow-sm p-4 mt-6">
      <h2 className="text-lg font-semibold mb-4">Telemetry Data</h2>
      <ResponsiveContainer width="100%" height={300}>
        <LineChart data={chartData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="time" />
          <YAxis yAxisId="left" />
          <YAxis yAxisId="right" orientation="right" />
          <Tooltip />
          <Legend />
          <Line
            yAxisId="left"
            type="monotone"
            dataKey="temperature"
            stroke="#ef4444"
            name="Temperature (°C)"
          />
          <Line
            yAxisId="right"
            type="monotone"
            dataKey="battery"
            stroke="#3b82f6"
            name="Battery (%)"
          />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}
