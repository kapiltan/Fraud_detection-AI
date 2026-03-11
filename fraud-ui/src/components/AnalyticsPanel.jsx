import { useEffect, useState } from "react";

export default function AnalyticsPanel({ token }) {

    const [stats, setStats] = useState(null);

    useEffect(() => {

        fetch("http://localhost:8080/analytics/summary", {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then(res => res.json())
            .then(setStats);

    }, [token]);

    if (!stats) return <p>Loading analytics...</p>;

    return (
        <div style={{ display: "flex", gap: "20px", marginBottom: "20px" }}>
            <div style={card}>
                <h3>Total Transactions</h3>
                <p>{stats.totalTransactions}</p>
            </div>

            <div style={card}>
                <h3>Total Amount</h3>
                <p>₹{stats.totalAmount}</p>
            </div>

            <div style={card}>
                <h3>Average Amount</h3>
                <p>₹{stats.averageAmount.toFixed(2)}</p>
            </div>
        </div>
    );
}

const card = {
    padding: "20px",
    border: "1px solid #ddd",
    borderRadius: "6px",
    width: "200px",
};