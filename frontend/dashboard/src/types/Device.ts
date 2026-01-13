export interface Device {
  id: string;
  name: string;
  status: "REGISTERED" | "ACTIVATED" | "DEACTIVATED";
  registeredAt: string;
  lastModifiedAt: string;
}
