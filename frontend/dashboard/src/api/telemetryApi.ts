import type { Telemetry } from "../types/Telemetry";

const API_BASE = "http://localhost:8080";

export async function fetchTelemetry(deviceId: string): Promise<Telemetry[]> {
  const response = await fetch(`${API_BASE}/api/telemetry/device/${deviceId}`);
  if (!response.ok) {
    throw new Error("Failed to fetch telemetry");
  }
  return response.json();
}
