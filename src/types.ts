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
  // order_id?: number;
  // email?: string;
}

export interface BillingData {
  apartment: string;
  email: string;
  floor: string;
  first_name: string;
  street: string;
  building: string;
  phone_number: string;
  shipping_method: string;
  postal_code: string;
  city: string;
  country: string;
  last_name: string;
  state: string;
}

export interface PaymobT {
  presentPayVC: ({
    billingData,
    paymentKey,
    saveCardDefault,
    showSaveCard,
    showAlerts,
    isEnglish,
    showScanCardButton,
  }: {
    billingData: BillingData;
    paymentKey: string;
    saveCardDefault: boolean;
    showSaveCard: boolean;
    showAlerts: boolean;
    isEnglish: boolean;
    showScanCardButton: boolean;
  }) => void;
}
