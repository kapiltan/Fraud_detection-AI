import { useEffect, useState } from "react";
import { fetchTransactions } from "../api/transactions";

export default function TransactionsTable() {
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadData = async () => {
      try {
        const data = await fetchTransactions();
        setTransactions(data);
      } catch (err) {
        setError("Failed to load transactions");
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, []);

  if (loading) return <p>Loading...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;

  return (
    <div style={{ padding: "1rem" }}>
      <h2>Transactions</h2>
      <table style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th style={th}>ID</th>
            <th style={th}>User</th>
            <th style={th}>Amount</th>
            <th style={th}>Timestamp</th>
          </tr>
        </thead>
        <tbody>
          {transactions.map(tx => (
            <tr key={tx._id || tx.id}>
              <td style={td}>{tx._id || tx.id}</td>
              <td style={td}>{tx.userId || tx.user}</td>
              <td style={td}>{tx.amount}</td>
              <td style={td}>{tx.timestamp}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

const th = {
  border: "1px solid #ddd",
  padding: "8px",
  background: "#f3f3f3",
};

const td = {
  border: "1px solid #ddd",
  padding: "8px",
};
