import type { Device } from "../types/Device";

interface Props {
  devices: Device[];
}

export function DashboardStats({ devices }: Props) {
  const total = devices.length;
  const activated = devices.filter((d) => d.status === "ACTIVATED").length;
  const deactivated = devices.filter((d) => d.status === "DEACTIVATED").length;
  const registered = devices.filter((d) => d.status === "REGISTERED").length;

  return (
    <div className="grid grid-cols-4 gap-4 mb-6">
      <div className="bg-white rounded-lg shadow-sm p-4">
        <p className="text-gray-500 text-sm">Total Devices</p>
        <p className="text-3xl font-bold">{total}</p>
      </div>
      <div className="bg-green-50 rounded-lg shadow-sm p-4">
        <p className="text-green-600 text-sm">Activated</p>
        <p className="text-3xl font-bold text-green-700">{activated}</p>
      </div>
      <div className="bg-gray-50 rounded-lg shadow-sm p-4">
        <p className="text-gray-500 text-sm">Deactivated</p>
        <p className="text-3xl font-bold text-gray-600">{deactivated}</p>
      </div>
      <div className="bg-blue-50 rounded-lg shadow-sm p-4">
        <p className="text-blue-600 text-sm">Registered</p>
        <p className="text-3xl font-bold text-blue-700">{registered}</p>
      </div>
    </div>
  );
}
