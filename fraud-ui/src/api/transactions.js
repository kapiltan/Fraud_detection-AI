import api from "./client";

export async function fetchTransactions() {
  const response = await api.get("/transactions/all");
  return response.data;
}
