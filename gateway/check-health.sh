#!/bin/bash
# Gateway health check script
# Usage: ./check-health.sh [gateway_url]

GATEWAY_URL="${1:-http://localhost:80}"

echo "=== API Gateway Health Check ==="
echo "Gateway: $GATEWAY_URL"
echo ""

# Check gateway health
echo "1. Gateway Status:"
curl -s "$GATEWAY_URL/gateway/health" | jq . 2>/dev/null || echo "  Failed to reach gateway"
echo ""

# Check all service routes
echo "2. Service Routes:"

services=(
    "fx-system:/api/v1/auth/login:POST"
    "fx-trade:/api/v1/symbols:GET"
    "fx-risk:/api/v1/risk/rules:GET"
    "fx-clearing:/api/v1/settlements:GET"
    "fx-crm:/api/v1/customers:GET"
    "fx-report:/api/v1/reports:GET"
    "fx-quote:/api/v1/market/quotes:GET"
    "fx-mt-bridge:/api/v1/mt/status:GET"
    "fx-fix-gateway:/api/v1/fix/status:GET"
)

for service in "${services[@]}"; do
    IFS=':' read -r name endpoint method <<< "$service"
    status=$(curl -s -o /dev/null -w "%{http_code}" -X "$method" "$GATEWAY_URL$endpoint" 2>/dev/null)
    if [ "$status" = "200" ] || [ "$status" = "401" ] || [ "$status" = "405" ]; then
        echo "  ✓ $name ($endpoint) - OK ($status)"
    else
        echo "  ✗ $name ($endpoint) - FAIL ($status)"
    fi
done

echo ""
echo "=== Done ==="
