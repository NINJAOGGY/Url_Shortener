import { useState } from "react";
import { deleteUrl } from "../api/urlApi";

export default function DeleteUrl() {

    const [shortCode, setShortCode] =
        useState("");

    const [message, setMessage] =
        useState("");

    const [loading, setLoading] =
        useState(false);

    async function handleDelete() {

        setLoading(true);
        setMessage("");

        try {

            await deleteUrl(shortCode);

            setMessage(
                "URL deleted successfully"
            );

        } catch {

            setMessage(
                "Failed to delete URL"
            );

        } finally {

            setLoading(false);
        }
    }

    return (
        <div className="bg-white p-6 rounded-xl shadow">

            <h2>Delete URL</h2>

            <input
                className="w-full border rounded-lg p-2"
                type="text"
                placeholder="Short Code"
                value={shortCode}
                onChange={(e) =>
                    setShortCode(
                        e.target.value
                    )
                }
            />

            <button
                className="mt-3 bg-red-600 text-white p-2 rounded-lg hover:bg-red-700"
                onClick={handleDelete}
                disabled={loading}
            >
                {
                    loading
                        ? "Deleting..."
                        : "Delete"
                }
            </button>

            {message  && (
                <p className="mt-3 text-green-600">{message}</p>
            )}

        </div>
    );
}