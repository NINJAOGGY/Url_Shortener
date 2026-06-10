import { useState } from "react";
import { getUrlStats } from "../api/urlApi";

type StatsResponse = {
    shortCode: string;
    longUrl: string;
    clickCount: number;
    createdAt: string;
};

export default function UrlStats() {

    const [shortCode, setShortCode] =
        useState("");

    const [stats, setStats] =
        useState<StatsResponse | null>(null);

    const [loading, setLoading] =
        useState(false);

    const [error, setError] =
        useState("");

    async function handleGetStats() {

        setLoading(true);
        setError("");

        try {

            const data =
                await getUrlStats(shortCode);

            setStats(data);

        } catch {

            setError(
                "URL not found"
            );

            setStats(null);

        } finally {

            setLoading(false);
        }
    }

    return (
        <div className="bg-white p-6 rounded-xl shadow">

            <h2>URL Analytics</h2>

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
                className="mt-3 w-full bg-purple-600 text-white p-2 rounded-lg hover:bg-purple-700"
                onClick={handleGetStats}
                disabled={loading}
            >
                {
                    loading
                        ? "Loading..."
                        : "Get Stats"
                }
            </button>

            {error && (
                <p>{error}</p>
            )}

            {stats && (
                <div className="mt-4 space-y-2">

                    <h3>Statistics</h3>

                    <p>
                        <strong>
                            Short Code:
                        </strong>{" "}
                        {stats.shortCode}
                    </p>

                    <p>
                        <strong>
                            Original URL:
                        </strong>{" "}
                        {stats.longUrl}
                    </p>

                    <p>
                        <strong>
                            Click Count:
                        </strong>{" "}
                        {stats.clickCount}
                    </p>

                    <p>
                        <strong>
                            Created At:
                        </strong>{" "}
                        {stats.createdAt}
                    </p>

                </div>
            )}

        </div>
    );
}