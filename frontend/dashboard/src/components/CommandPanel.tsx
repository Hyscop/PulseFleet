import { useState } from "react";
import { sendCommand, type CommandRequest } from "../api/commandApi";

interface Props {
  deviceId: string;
}

export function CommandPanel({ deviceId }: Props) {
  const [loading, setLoading] = useState(false);
  const [lastResult, setLastResult] = useState<string | null>(null);

  const handleSendCommand = async (type: CommandRequest["type"]) => {
    setLoading(true);
    setLastResult(null);

    try {
      const result = await sendCommand({ deviceId, type });
      setLastResult(`✓ ${type} sent (${result.status}) `);
    } catch (err) {
      setLastResult(`✗ Failed to send ${type} Error:${err}`);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="bg-white rounded-lg shadow-sm p-4 mt-6">
      <h2 className="text-lg font-semibold mb-4">Send Command</h2>
      <div className="flex gap-2">
        <button
          disabled={loading}
          onClick={() => handleSendCommand("REBOOT")}
          className="bg-orange-500 text-white px-4 py-2 rounded hover:bg-orange-600 disabled:opacity-50"
        >
          Reboot
        </button>
        <button
          onClick={() => handleSendCommand("BLINK_LED")}
          disabled={loading}
          className="bg-purple-500 text-white px-4 py-2 rounded hover:bg-purple-600 disabled:opacity-50"
        >
          Blink LED
        </button>
        <button
          onClick={() => handleSendCommand("UPDATE_CONFIG")}
          disabled={loading}
          className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 disabled:opacity-50"
        >
          Update Config
        </button>
      </div>
      {lastResult && (
        <p
          className={`mt-2 text-sm ${
            lastResult.startsWith("✓") ? "text-green-600" : "text-red-600"
          }`}
        >
          {lastResult}
        </p>
      )}
    </div>
  );
}
