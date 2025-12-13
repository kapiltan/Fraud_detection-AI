import { useState, useEffect } from "react";
import { useGoogleLogin, googleLogout } from "@react-oauth/google";

import TransactionsTable from "./components/TransactionsTable";
import AlertsTable from "./components/AlertsTable";
// import LearningPanel from "./components/LearningPanel";
// import InsightCharts from "./components/InsightCharts";

function App() {
  const [user, setUser] = useState(() => {
    const stored=localStorage.getItem("fd_user");
    return stored?JSON.parse(stored):null;
  });

  const [appToken, setAppToken] = useState(() =>{
    return localStorage.getItem("fd_token") || null;
  });

  useEffect(() => {
    if(user) localStorage.setItem("fd_user",JSON.stringify(user));
    else localStorage.removeItem("fd_user");
  }, [user]);

  useEffect(() => {
     if(appToken) localStorage.setItem("fd_token", appToken);
     else localStorage.removeItem("fd_token");
  }, [appToken]);

  const login = useGoogleLogin({
    scope: "openid profile email https://www.googleapis.com/auth/userinfo.profile",
    onSuccess: async (tokenResponse) => {
      try {
        const res = await fetch("https://www.googleapis.com/oauth2/v3/userinfo?alt=json", {
          headers: { Authorization: `Bearer ${tokenResponse.access_token}` },
        });
        const profile = await res.json();

        if(profile.picture){
            const baseUrl = profile.picture.split("=")[0];
            profile.picture = `${baseUrl}=s96-c`;
        }

        const backendRes= await fetch("http://localhost:8080/api/auth/google", {
           method: "POST",
           headers: {"Content-Type" : "application/json"},
           body: JSON.stringify({
              accessToken: tokenResponse.access_token,
           }),
        });

        if(!backendRes.ok){
            console.error("Backend auth failed");
        }
        else{
            const data = await backendRes.json();
            setAppToken(data.jwt || null);
        }

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
    setAppToken(null);
  };

  const isAuthenticated = !!user && !!appToken;

  return (
    <div
        style={{
          height: "100vh",
          width: "100vw",
          backgroundColor: "#181818",
          color: "#fff",
          padding: "24px",
          boxSizing: "border-box",
          overflowY: "auto",
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
              padding: "8px 16px",
              borderRadius: "999px",
              minWidth: "0",
              maxWidth: "100%",
              flexShrink: 0,
            }}
          >
            {user.picture && (
                <img
                  src={user.picture}
                  alt="avatar"
                  style={{
                    width: 40,
                    height: 40,
                    borderRadius: "50%",
                    objectFit: "cover",
                    flexShrink: 0,
                  }}
                />
            )}
            <div
              style={{
                textAlign: "right",
                minWidth: 0,
                overflow: "hidden",        // FIXED
                textOverflow: "ellipsis",
                whiteSpace: "nowrap",
              }}
            >
              <strong>{user.name}</strong>
              <div style={{ fontSize: "0.75rem", opacity: 0.7 }}>
                {user.email}
              </div>
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

      <div style={{ display:"flex", flexDirection:"column", gap:"20px"}}>
      <div style={{ display:"flex", flexDirection:"column", gap:"20px" }}>
      <div style={{ marginTop: "10px" }}>
        <TransactionsTable token={appToken} isAuthenticated={isAuthenticated} />
        <AlertsTable token={appToken} isAuthenticated={isAuthenticated}/>
      </div>
      </div>
      </div>
    </div>
  );
}

export default App;
