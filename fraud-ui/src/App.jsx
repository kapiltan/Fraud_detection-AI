import TransactionsTable from "./components/TransactionsTable";
import AlertsTable from "./components/AlertsTable"

function App() {
  return (
    <div style={{ fontFamily: "sans-serif" }}>
      <h1>Fraud Detection Dashboard</h1>
      <TransactionsTable />
      <AlertsTable />
    </div>
  );
}

export default App;
