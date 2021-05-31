module.exports = {
  root: true,
  extends: ['@react-native-community', 'eslint-config-prettier'],
  rules: {
    eqeqeq: 'warn',
    'no-shadow': 'off',
    'prettier/prettier': 'off',
    '@typescript-eslint/no-unused-vars': 'warn',
    'react-native/no-unused-styles': 'warn',
    'no-console': 'warn',
    'react-native/no-inline-styles': 'warn',
  },
};