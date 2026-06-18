export interface LedgerResponse {
  ledgerId: number;
  paymentId: number;
  senderUserId: number;
  receiverUserId: number;
  amount: number;
  currency: string;
  status: string;
  remarks: string;
  transactionTime: string;
}
