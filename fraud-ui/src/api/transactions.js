import api from "./client";

export async function fetchTransactions() {
  const response = await api.get("/api/transactions");
  return response.data;
}
