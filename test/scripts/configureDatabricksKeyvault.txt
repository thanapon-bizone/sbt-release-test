# #!/bin/bash

##############################################
# ** WARNING! This will overwrite the databrickscfg file in your machine 
# **Add `MSYS_NO_PATHCONV=1` before `databricks secrets create-scope..`  when running the scipt in GIT Bash
#
# Reference
# - https://learn.microsoft.com/en-us/azure/databricks/security/secrets/secret-scopes#--create-an-azure-key-vault-backed-secret-scope-using-the-databricks-cli
# - https://learn.microsoft.com/en-us/azure/databricks/dev-tools/api/latest/aad/user-aad-token
# - https://learn.microsoft.com/en-us/azure/databricks/dev-tools/cli/
# - https://github.com/fengzhou-msft/azure-cli/blob/ea149713de505fa0f8ae6bfa5d998e12fc8ff509/doc/use_cli_with_git_bash.md#auto-translation-of-resource-ids
##############################################


databricksurl=$1
scopename=$2
keyvaultdnsname=$3
keyvaultresourceid=$4
subscriptionid=$5

configureDatabricksCLI() {

  # echo "installing databricks CLI"
  # pip install databricks-cli

  echo "configuring databrick-cli authentication"

  declare DATABRICKS_URL=${databricksurl}
  declare DATABRICKS_ACCESS_TOKEN=$(az account get-access-token --resource 2ff814a6-3304-4ab8-85cb-cd0e6f879c1d --query "accessToken" --output tsv)

  declare dbconfig=$(<~/.databrickscfg)

  if [[ -z "$DATABRICKS_URL" || -z "$DATABRICKS_ACCESS_TOKEN" ]]; then
    echo "file [~/.databrickscfg] is not configured, but [DATABRICKS_URL],[DATABRICKS_ACCESS_TOKEN] env vars are not set"
    exit 1
  else
    echo "populating [~/.databrickscfg]"
    > ~/.databrickscfg
    echo "[DEFAULT]" >> ~/.databrickscfg
    echo "host = $DATABRICKS_URL" >> ~/.databrickscfg
    echo "token = $DATABRICKS_ACCESS_TOKEN" >> ~/.databrickscfg
    echo "" >> ~/.databrickscfg
  fi


}

configureDatabricksKeyVault() {

  declare SCOPE_NAME=${scopename}
  declare KEYVAULT_RESOURCE_ID=${keyvaultresourceid}
  declare KEYVAULT_DNS_NAME=${keyvaultdnsname}

  databricks secrets create-scope --scope ${SCOPE_NAME} --scope-backend-type AZURE_KEYVAULT --resource-id ${KEYVAULT_RESOURCE_ID} --dns-name ${KEYVAULT_DNS_NAME} --initial-manage-principal users

  compileResult=$?

  if [ $compileResult -ne 0 ]
  then
      echo "Error Setting up Databricks Keyvault Secret scope"
      exit $compileResult
  fi

}


echo "Azure CLI login into boliden tenant..."
az login --tenant 496a4ab1-caef-478f-938d-6551aca7fb85

echo "Set default subscription..."
az account set --subscription ${subscriptionid}

echo "Configure Databricks CLI..."
configureDatabricksCLI

echo "Setup Databricks keyvault secret scope..."
configureDatabricksKeyVault
