import { PaymentStatus } from './payment-status';

export interface PaymentResponse {
  paymentId: number;
  senderUserId: number;
  receiverUserId: number;
  amount: number;
  status: PaymentStatus;
  createdAt: string;
  failureReason: string | null;
}
