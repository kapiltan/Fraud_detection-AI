import TransactionsTable from "./components/TransactionsTable";
import AlertsTable from "./components/AlertsTable";
import { useState } from "react";
import { useGoogleLogin, googleLogout } from "@react-oauth/google";

function App() {
  const [user, setUser] = useState(null);

  const login = useGoogleLogin({
    scope: "openid profile email",
    onSuccess: async (tokenResponse) => {
      try {
        const res = await fetch("https://www.googleapis.com/oauth2/v3/userinfo", {
          headers: { Authorization: `Bearer ${tokenResponse.access_token}` },
        });
        const profile = await res.json();
        setUser(profile);
      } catch (err) {
        console.error("Error fetching user info:", err);
      }
    },
    onError: (err) => console.error("Login Failed:", err),
  });

  const handleLogout = () => {
    googleLogout();
    setUser(null);
  };

  return (
    <div
      style={{
        minHeight: "100vh",
        backgroundColor: "#181818",
        color: "#fff",
        padding: "24px",
        boxSizing: "border-box",
      }}
    >
      {/* Header */}
      <header
        style={{
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between",
          marginBottom: "20px",
        }}
      >
        <h1 style={{ margin: 0 }}>Fraud Detection Dashboard</h1>

        {/* Profile / Login */}
        {user ? (
          <div
            style={{
              display: "flex",
              alignItems: "center",
              gap: "12px",
              background: "#252525",
              padding: "8px 14px",
              borderRadius: "999px",
            }}
          >
            {user.picture && (
              <img
                src={user.picture}
                alt="avatar"
                style={{ width: 36, height: 36, borderRadius: "50%" }}
              />
            )}
            <div style={{ textAlign: "right" }}>
              <strong>{user.name}</strong>
              <div style={{ fontSize: "0.75rem", opacity: 0.7 }}>{user.email}</div>
            </div>
            <button
              onClick={handleLogout}
              style={{
                padding: "4px 10px",
                borderRadius: "20px",
                border: "none",
                cursor: "pointer",
                fontWeight: "bold",
              }}
            >
              Logout
            </button>
          </div>
        ) : (
          <button
            onClick={() => login()}
            style={{
              padding: "10px 18px",
              borderRadius: "999px",
              border: "none",
              cursor: "pointer",
              backgroundColor: "#fff",
              color: "#000",
              fontWeight: "bold",
              display: "flex",
              alignItems: "center",
              gap: "8px",
            }}
          >
            Sign in With Google
          </button>
        )}
      </header>

      {/* Demo Mode Notice */}
      {!user && (
        <div
          style={{
            background: "#303030",
            padding: "12px 16px",
            borderRadius: "6px",
            marginBottom: "22px",
            fontSize: "0.9rem",
          }}
        >
          <strong>Demo Mode:</strong> Sign in to unlock personalized analytics.
        </div>
      )}

      {/* Live Data Tables */}
      <div style={{ marginTop: "10px" }}>
        <TransactionsTable />
        <AlertsTable />
      </div>
    </div>
  );
}

export default App;
