import { useState } from "react";
import { createDevice } from "../api/deviceApi";

interface Props {
  onDeviceAdded: () => void;
}

export function AddDeviceForm({ onDeviceAdded }: Props) {
  const [name, setName] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      await createDevice(name);
      setName("");
      onDeviceAdded();
    } catch (err) {
      setError(err instanceof Error ? err.message : "Unknown error");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="bg-white p-4 rounded-lg shadow-sm mb-6"
    >
      <h2 className="text-lg font-semibold mb-4">Add New Device</h2>
      {error && <p className="text-red-500 mb-2">{error}</p>}
      <div className="flex gap-4">
        <input
          type="text"
          placeholder="Device Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
          className="flex-1 border rounded px-3 py-2"
        />
        <button
          type="submit"
          disabled={loading}
          className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 disabled:opacity-50"
        >
          {loading ? "Adding..." : "Add Device"}
        </button>
      </div>
    </form>
  );
}
