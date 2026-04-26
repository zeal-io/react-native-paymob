export interface PayResponse {
  amount_cents: number;
  is_refunded: boolean;
  is_capture: boolean;
  captured_amount: number;
  source_data_type: string;
  pending: boolean;
  is_3d_secure: boolean;
  id: number;
  is_void: boolean;
  currency: string;
  is_auth: boolean;
  is_refund: boolean;
  owner: number;
  is_voided: boolean;
  source_data_pan: string;
  profile_id: number;
  success: boolean;
  dataMessage: string;
  source_data_sub_type: string;
  error_occured: boolean;
  is_standalone_payment: boolean;
  created_at: string;
  refunded_amount_cents: number;
  integration_id: number;
  order: number;
}

export interface SaveCardResponse {
  card_subtype: string;
  id: number;
  token: string;
  created_at: string;
  masked_pan: string;
  merchant_id: number;
}

export interface BillingData {
  apartment: string;
  email: string;
  floor: string;
  first_name: string;
  building: string;
  phone_number: string;
  postal_code: string;
  city: string;
  country: string;
  last_name: string;
  state: string;
}

export type PaymobEventType =
  | "userDidCancel"
  | "missingArgument"
  | "paymentAttemptFailed"
  | "transactionRejected"
  | "transactionAccepted"
  | "transactionAcceptedWithCard"
  | "userDidCancel3dSecureVerification";

export interface PaymobEventPayload {
  type: PaymobEventType;
}

export interface UserDidCancelEvent extends PaymobEventPayload {
  type: "userDidCancel";
}

export interface MissingArgumentEvent extends PaymobEventPayload {
  type: "missingArgument";
  missingKey: string;
}

export interface PaymentAttemptFailedEvent extends PaymobEventPayload {
  type: "paymentAttemptFailed";
  detailedDescription: string;
}

export interface TransactionRejectedEvent extends PaymobEventPayload {
  type: "transactionRejected";
  payData: PayResponse;
  rawResponse?: string;
}

export interface TransactionAcceptedEvent extends PaymobEventPayload {
  type: "transactionAccepted";
  payData: PayResponse;
  rawResponse?: string;
}

export interface TransactionAcceptedWithCardEvent extends PaymobEventPayload {
  type: "transactionAcceptedWithCard";
  payData: PayResponse;
  savedCardData: SaveCardResponse;
}

export interface UserDidCancel3dSecureVerificationEvent extends PaymobEventPayload {
  type: "userDidCancel3dSecureVerification";
  pendingPayData?: string | PayResponse;
  rawResponse?: string;
}

export type PaymobEvent =
  | UserDidCancelEvent
  | MissingArgumentEvent
  | PaymentAttemptFailedEvent
  | TransactionRejectedEvent
  | TransactionAcceptedEvent
  | TransactionAcceptedWithCardEvent
  | UserDidCancel3dSecureVerificationEvent;

export interface PaymobT {
  presentPayVC: (params: {
    billingData: BillingData;
    paymentKey: string;
    saveCardDefault: boolean;
    showSaveCard: boolean;
    showAlerts: boolean;
    isEnglish: boolean;
    buttonText?: string;
    cardToken?: string;
    maskedCardNumber?: string;
  }) => void;
}