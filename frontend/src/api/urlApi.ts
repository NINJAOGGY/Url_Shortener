const API_BASE_URL = "http://localhost:8080";

export async function getUrlStats(
    shortCode: string
) {

    const response = await fetch(
        `${API_BASE_URL}/api/v1/urls/${shortCode}/stats`
    );

    if (!response.ok) {
        throw new Error(
            "Failed to fetch stats"
        );
    }

    return response.json();
}

export async function deleteUrl(
    shortCode: string
) {

    const response = await fetch(
        `${API_BASE_URL}/api/v1/urls/${shortCode}`,
        {
            method: "DELETE"
        }
    );

    if (!response.ok) {
        throw new Error(
            "Failed to delete URL"
        );
    }
}

export async function createShortUrl(
    longUrl: string,
    customAlias?: string
) {

    const response = await fetch(
        `${API_BASE_URL}/api/v1/urls`,
        {
            method: "POST",
            headers: {
                "Content-Type":
                    "application/json",
            },
            body: JSON.stringify({
                longUrl,
                customAlias,
            }),
        }
    );

    if (!response.ok) {
        throw new Error(
            "Failed to create URL"
        );
    }

    return response.json();
}