import { useEffect, useState } from "react";
import { fetchTransactions } from "../api/transactions";

export default function TransactionsTable({ token, isAuthenticated }) {
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [page, setPage] = useState(0);

  const size = 5;

  console.log("Sending token: ", token);

  useEffect(() => {

    const loadData = async () => {
      try {

        let response;

        if (token) {
          console.log("Fetching REAL transactions");

          response = await fetch(`http://localhost:8080/transactions?page=${page}&size=${size}&sort=timestamp`, {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });

        } else {
          console.log("Fetching DEMO transactions");

          response = await fetch("http://localhost:8080/api/public/transactions");
        }

        const data = await response.json();

        if (!Array.isArray(data)) {
          console.error("Backend returned error:", data);
          return;
        }

        setTransactions(data);

      } catch (err) {
        setError("Failed to load transactions");
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    loadData();

    const interval = setInterval(loadData, 3000);
    return () => clearInterval(interval);

  }, [token, page]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;

  return (
    <div style={{ padding: "1rem" }}>
      <h2>Transactions</h2>
      <table style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th style={th} >ID</th>
            <th style={th}>User</th>
            <th style={th}>Amount</th>
            <th style={th}>Timestamp</th>
          </tr>
        </thead>
        <tbody>
          {transactions.map(tx => (
            <tr key={tx._id || tx.id}>
              <td>{tx._id || tx.id}</td>
              <td>{tx.userId || tx.user}</td>
              <td style={td}>{tx.amount}</td>
              <td>{tx.timestamp ? tx.timestamp.replace("T", " ") : "-"}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <button
        onClick={() => setPage((p) => Math.max(p - 1, 0))}
        disabled={page === 0}
      >
        Previous
      </button>

      <span style={{ margin: "0 10px" }}>
        Page {page + 1}
      </span>

      <button
        onClick={() => setPage((p) => p + 1)}
      >
        Next
      </button>
    </div>
  );
}

const th = {
  border: "1px solid #ddd",
  padding: "8px",
  background: "#f3f3f3",
  color: "#000",
};

const td = {
  border: "1px solid #ddd",
  padding: "8px",
};
