import type { Device } from "../types/Device";

const API_BASE = "http://localhost:8081/api";

export async function fetchDevices(): Promise<Device[]> {
  const response = await fetch(`${API_BASE}/devices`);
  if (!response.ok) {
    throw new Error("Failed to fetch devices");
  }
  return response.json();
}

export async function createDevice(name: string): Promise<Device> {
  const response = await fetch(`${API_BASE}/devices`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name }),
  });
  if (!response.ok) {
    throw new Error("Failed to create device");
  }
  return response.json();
}

export async function fetchDevice(id: string): Promise<Device> {
  const response = await fetch(`${API_BASE}/devices/${id}`);
  if (!response.ok) {
    throw new Error("Device not founnd");
  }
  return response.json();
}
