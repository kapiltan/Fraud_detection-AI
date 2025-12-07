import {useEffect, useState} from "react";

export default function AlertsTable(){
    const [alerts, setAlerts] = useState([]);

    const fetchAlerts = async () => {
        try{
            const response = await fetch("http://localhost:8080/alerts");
            const data = await response.json();
            setAlerts(data);
        }
        catch (error){
            console.error("Error fetching alerts:", error);
        }
    };

    useEffect (() => {
       fetchAlerts();
       const interval = setInterval(fetchAlerts,5000);
       return () => clearInterval(interval);
    }, []);

    return (<div className="alerts-container">
        <h2>Fraud Alerts</h2>

        <table border="1" cellPadding="8">
            <thead>
                <tr>
                    <th>User</th>
                    <th>Amount</th>
                    <th>Reason</th>
                    <th>Alert Time</th>
                </tr>
            </thead>
            <tbody>
                {alerts.map((alert) => (
                    <tr key={alert.id} style={{ backgroundColor: "#ffcccc" }}>
                        <td>{alert.userId}</td>
                        <td>{alert.amount}</td>
                        <td>{alert.reason}</td>
                        <td>{alert.alertTime}</td>
                    </tr>
                ))}
            </tbody>
        </table>

    </div>);
}