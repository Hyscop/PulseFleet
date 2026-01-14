import { useEffect, useState } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import type { Device } from "../types/Device";
import {
  fetchDevice,
  activateDevice,
  deactivateDevice,
  deleteDevice,
  renameDevice,
} from "../api/deviceApi";
import { TelemetryChart } from "./TelemetryChart";

export function DeviceDetail() {
  const { id } = useParams<{ id: string }>();
  const [device, setDevice] = useState<Device | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();
  const [editing, setEditing] = useState(false);
  const [newName, setNewName] = useState("");

  useEffect(() => {
    if (!id) return;

    let cancelled = false;

    fetchDevice(id)
      .then((data) => {
        if (!cancelled) setDevice(data);
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
  }, [id]);

  const handleActivate = async () => {
    if (!id) return;
    try {
      const updated = await activateDevice(id);
      setDevice(updated);
    } catch (err) {
      setError(err instanceof Error ? err.message : "Failed to activate");
    }
  };

  const handleDeactivate = async () => {
    if (!id) return;
    try {
      const updated = await deactivateDevice(id);
      setDevice(updated);
    } catch (err) {
      setError(err instanceof Error ? err.message : "Failed to deactivate");
    }
  };

  const handleDelete = async () => {
    if (!id) {
      return;
    }
    if (!confirm("Are you sure you want to delete this device")) {
      return;
    }
    try {
      await deleteDevice(id);
      navigate("/");
    } catch (err) {
      setError(err instanceof Error ? err.message : "Failed to delete");
    }
  };

  const handleRename = async () => {
    if (!id || !newName.trim()) {
      return;
    }
    try {
      const updated = await renameDevice(id, newName);
      setDevice(updated);
      setEditing(false);
    } catch (err) {
      setError(err instanceof Error ? err.message : "Failed to rename");
    }
  };

  const startEditing = async () => {
    setNewName(device?.name || "");
    setEditing(true);
  };

  if (loading) return <div className="p-4">Loading...</div>;
  if (error) return <div className="p-4 text-red-500">Error: {error}</div>;
  if (!device) return <div className="p-4">Device not found</div>;

  return (
    <div className="p-4">
      <Link to="/" className="text-blue-500 hover:underline mb-4 inline-block">
        ← Back to devices
      </Link>

      <div className="bg-white rounded-lg shadow-sm p-6">
        {editing ? (
          <div className="flex gap-2 mb-4">
            <input
              type="text"
              value={newName}
              onChange={(e) => setNewName(e.target.value)}
              className="border rounded px-3 py-2 flex-1"
            />
            <button
              onClick={handleRename}
              className="bg-blue-500 text-white px-4 py-2 rounded"
            >
              Save
            </button>
            <button
              onClick={() => setEditing(false)}
              className="bg-gray-300 px-4 py-2 rounded"
            >
              Cancel
            </button>
          </div>
        ) : (
          <div className="flex items-center gap-2 mb-4">
            <h1 className="text-2xl font-bold">{device.name}</h1>
            <button
              onClick={startEditing}
              className="text-blue-500 hover:underline text-sm"
            >
              Edit
            </button>
          </div>
        )}
        <div className="grid grid-cols-2 gap-4">
          <div>
            <p className="text-gray-500 text-sm">Status</p>
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
          <div>
            <p className="text-gray-500 text-sm">Device ID</p>
            <p className="font-mono text-sm">{device.id}</p>
          </div>

          <div>
            <p className="text-gray-500 text-sm">Registered</p>
            <p>{new Date(device.registeredAt).toLocaleString()}</p>
          </div>

          <div>
            <p className="text-gray-500 text-sm">Last Modified</p>
            <p>{new Date(device.lastModifiedAt).toLocaleString()}</p>
          </div>
          <div className="mt-6 flex gap-4">
            {device.status !== "ACTIVATED" && (
              <button
                onClick={handleActivate}
                className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600"
              >
                Activate
              </button>
            )}
            {device.status === "ACTIVATED" && (
              <button
                onClick={handleDeactivate}
                className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
              >
                Deactivate
              </button>
            )}
            <button
              onClick={handleDelete}
              className="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600"
            >
              Delete
            </button>
          </div>
        </div>
      </div>
      {device.status === "ACTIVATED" && <TelemetryChart deviceId={device.id} />}
    </div>
  );
}
