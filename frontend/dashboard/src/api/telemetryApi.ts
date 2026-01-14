import type { Telemetry } from "../types/Telemetry";

const API_BASE = "http://localhost:8082/api";

export async function fetchTelemetry(deviceId: string): Promise<Telemetry[]> {
  const response = await fetch(`${API_BASE}/telemetry/device/${deviceId}`);
  if (!response.ok) {
    throw new Error("Failed to fetch telemetry");
  }
  return response.json();
}
