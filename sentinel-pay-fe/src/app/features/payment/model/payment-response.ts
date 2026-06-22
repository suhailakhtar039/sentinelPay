export interface PaymentResponse {
  paymentId: number;
  senderUserId: number;
  receiverUserId: number;
  amount: number;
  status: string;
  createdAt: string;
  failureReason: string | null;
}
