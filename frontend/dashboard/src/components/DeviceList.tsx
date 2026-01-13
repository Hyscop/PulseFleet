import { useEffect, useState } from "react";
import type { Device } from "../types/Device";
import { fetchDevices } from "../api/deviceApi";
import { AddDeviceForm } from "./AddDeviceForm";
import { Link } from "react-router-dom";

export function DeviceList() {
  const [devices, setDevices] = useState<Device[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [refreshKey, setRefreshKey] = useState(0);

  useEffect(() => {
    let cancelled = false;

    fetchDevices()
      .then((data) => {
        if (!cancelled) setDevices(data);
      })
      .catch((err) => {
        if (!cancelled) setError(err.message);
      })
      .finally(() => {
        if (!cancelled) setLoading(false);
      });

    return () => {
      cancelled = true;
    };
  }, [refreshKey]);

  const handleDeviceAdded = () => {
    setLoading(true);
    setRefreshKey((k) => k + 1);
  };

  if (loading) {
    return <div className="p-4">Loading...</div>;
  }

  if (error) {
    return <div className="p-4 text-red-500">Error: {error}</div>;
  }

  return (
    <div className="p-4">
      <AddDeviceForm onDeviceAdded={handleDeviceAdded} />
      <h1 className="text-2xl font-bold mb-4">Devices</h1>
      <div className="grid gap-4">
        {devices.map((device) => (
          <Link key={device.id} to={`/devices/${device.id}`} className="block">
            <div className="border rounded-lg p-4 shadow-sm hover:shadow-md transition-shadow">
              <h2 className="font-semibold">{device.name}</h2>
              <p className="text-gray-500 text-sm">
                Registered: {new Date(device.registeredAt).toLocaleDateString()}
              </p>
              <span
                className={`inline-block px-2 py-1 text-sm rounded ${
                  device.status === "ACTIVATED"
                    ? "bg-green-100 text-green-800"
                    : device.status === "DEACTIVATED"
                    ? "bg-gray-100 text-gray-800"
                    : "bg-blue-100 text-blue-800"
                }`}
              >
                {device.status}
              </span>
            </div>
          </Link>
        ))}
      </div>
    </div>
  );
}
