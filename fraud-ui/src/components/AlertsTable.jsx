import {useEffect, useState} from "react";
import {ToastContainer, toast} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export default function AlertsTable(){
    const [alerts, setAlerts] = useState([]);
    const [seenAlertIds, setSeenAlertIds] = useState(new Set());

    const fetchAlerts = async () => {
        try{
            const response = await fetch("http://localhost:8080/alerts");
            const data = await response.json();
            setAlerts([...data].reverse());
        }
        catch (error){
            console.error("Error fetching alerts:", error);
        }
    };

    useEffect (() => {
       fetchAlerts();
       const interval = setInterval(fetchAlerts,3000);
       return () => clearInterval(interval);
    }, []);

//     console.log(alerts);

    useEffect(() => {
        if(alerts.length === 0) return;

        const latest = new Set(seenAlertIds);

        alerts.forEach(alert => {
            if(alert.severity === "HIGH" && !latest.has(alert.transactionId)){
                toast.error(`HIGH! Fraud Alert - ${alert.userId} | Rs.${alert.Amount}`, {position: "top-right", autoClose: 4000 });
                latest.add(alert.transactionId);
            }
        });

        setSeenAlertIds(latest);

    },[alerts]);

    return (<div className="alerts-container">
        <h2>Fraud Alerts</h2>

        <table border="1" cellPadding="8" style={{ width: "100%", textAlign: "center" }}>
            <thead>
                <tr>
                    <th>User</th>
                    <th>Amount</th>
                    <th>Severity</th>
                    <th>Reason</th>
                    <th>Alert Time</th>
                </tr>
            </thead>
            <tbody>
                {alerts.map((alert) => (
                    <tr key={alert.id} style={{ backgroundColor: alert.severity=="HIGH" ? "#ffe6e6" :  alert.severity == "MEDIUM"? "#fff7cc" : "white" , color: "#000"}}>
                        <td>{alert.userId}</td>
                        <td>{alert.amount}</td>
                        <td><span style={{padding: "4px 10px", backgroundColor: alert.severity=="HIGH" ? "#e60000" : alert.severity == "MEDIUM"? "#ff9900" : "#0077ff", color:"#ffffff", borderRadius:"6px", fontWeight:"bold"}}>{alert.severity}</span></td>
                        <td>{alert.reason}</td>
                        <td>{alert.alertTime}</td>
                    </tr>
                ))}
            </tbody>
        </table>
        <ToastContainer/>
    </div>);
}