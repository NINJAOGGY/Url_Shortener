import { useState } from "react";
import { createShortUrl } from "../api/urlApi";

type CreateResponse = {
    shortCode: string;
    shortUrl: string;
    longUrl: string;
};

export default function CreateUrlForm() {

    const [longUrl, setLongUrl] =
        useState("");

    const [customAlias, setCustomAlias] =
        useState("");

    const [result, setResult] =
        useState<CreateResponse | null>(null);

    const [loading, setLoading] =
        useState(false);

    const [error, setError] =
        useState("");

    async function handleSubmit(
        e: React.FormEvent
    ) {

        e.preventDefault();

        setLoading(true);
        setError("");

        try {

            const data =
                await createShortUrl(
                    longUrl,
                    customAlias || undefined
                );

            setResult(data);
            setLongUrl("");
            setCustomAlias("");

        } catch {

            setError(
                "Failed to create URL"
            );

        } finally {

            setLoading(false);
        }
    }

    async function copyToClipboard(
        text: string
    ) {

        await navigator.clipboard.writeText(
            text
        );
    }

    return (
        <div className="bg-white p-6 rounded-xl shadow">

            <h2>Create Short URL</h2>

            <form onSubmit={handleSubmit}>

                <input
                    className="w-full border rounded-lg p-2"
                    type="text"
                    placeholder="Long URL"
                    value={longUrl}
                    onChange={(e) =>
                        setLongUrl(
                            e.target.value
                        )
                    }
                />

                <br /><br />

                <input
                    className="w-full border rounded-lg p-2"
                    type="text"
                    placeholder="Custom Alias (optional)"
                    value={customAlias}
                    onChange={(e) =>
                        setCustomAlias(
                            e.target.value
                        )
                    }
                />

                <br /><br />

                <button
                    className="w-full bg-blue-600 text-white rounded-lg p-2 hover:bg-blue-700"
                    type="submit"
                    disabled={loading}
                >
                    {
                        loading
                            ? "Creating..."
                            : "Create"
                    }
                </button>

            </form>

            {error && (
                <p>{error}</p>
            )}

            {result && (

                <div className="mt-4 p-4 bg-green-50 rounded-lg">

                    <h3>
                        URL Created
                    </h3>

                    <p>
                        {result.shortUrl}
                    </p>

                    <button className="mt-2 bg-green-600 text-white px-3 py-1 rounded-lg hover:bg-green-700" onClick={() =>
                        copyToClipboard(
                            result.shortUrl
                        )
                        
                    }>
                        Copy Url
                    </button>

                </div>
            )}

        </div>
    );
}