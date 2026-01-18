const API_BASE = "http://localhost:8080";

export interface CommandRequest {
  deviceId: string;
  type: "REBOOT" | "UPDATE_CONFIG" | "BLINK_LED";
  payload?: string;
}

export interface CommandResponse {
  id: string;
  deviceId: string;
  type: string;
  payload: string | null;
  status: string;
  createdAt: string;
  sentAt: string | null;
  acknowledgeAt: string | null;
}

export async function sendCommand(
  request: CommandRequest
): Promise<CommandResponse> {
  const response = await fetch(`${API_BASE}/api/commands`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(request),
  });
  if (!response.ok) {
    throw new Error("Failed to send command");
  }
  return response.json();
}

export async function getCommandsByDevice(
  deviceId: string
): Promise<CommandResponse[]> {
  const response = await fetch(`${API_BASE}/api/commands/device/${deviceId}`);
  if (!response.ok) {
    throw new Error("Failed to fetch commands");
  }
  return response.json();
}
