#!/usr/bin/env pwsh
# Deploy RSVP Reader to GitHub
# This script automates the GitHub repository creation and push process

param(
    [Parameter(Mandatory=$false)]
    [string]$GitHubUsername = "",
    [Parameter(Mandatory=$false)]
    [switch]$UseGHCLI = $false
)

$scriptPath = Split-Path -Parent $MyInvocation.MyCommand.Path
$projectDir = $scriptPath

Write-Host "╔════════════════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║         RSVP Reader Wear OS - GitHub Deployment Script            ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""

# Check if Git is installed
Write-Host "✓ Checking prerequisites..." -ForegroundColor Yellow
if (-not (Get-Command git -ErrorAction SilentlyContinue)) {
    Write-Host "✗ Git is not installed. Please install Git first." -ForegroundColor Red
    exit 1
}
Write-Host "✓ Git is installed" -ForegroundColor Green

# Check if GitHub CLI is installed (if requested)
if ($UseGHCLI) {
    if (-not (Get-Command gh -ErrorAction SilentlyContinue)) {
        Write-Host "✗ GitHub CLI is not installed. Please install it or use manual method." -ForegroundColor Red
        exit 1
    }
    Write-Host "✓ GitHub CLI is installed" -ForegroundColor Green
}

Write-Host ""

if ($UseGHCLI) {
    Write-Host "📦 Using GitHub CLI for deployment..." -ForegroundColor Cyan
    Write-Host ""
    
    # Check if already authenticated
    gh auth status *>$null
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Authenticating with GitHub CLI..." -ForegroundColor Yellow
        gh auth login
        if ($LASTEXITCODE -ne 0) {
            Write-Host "✗ Authentication failed" -ForegroundColor Red
            exit 1
        }
    }
    
    Write-Host ""
    Write-Host "Creating repository on GitHub..." -ForegroundColor Yellow
    gh repo create rsvp_reader_ws --public --source="$projectDir" --remote=origin --push
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "╔════════════════════════════════════════════════════════════════════╗" -ForegroundColor Green
        Write-Host "║              ✓ Repository Created Successfully!                    ║" -ForegroundColor Green
        Write-Host "╚════════════════════════════════════════════════════════════════════╝" -ForegroundColor Green
        Write-Host ""
        Write-Host "Repository URL: https://github.com/$(gh api user --jq .login)/rsvp_reader_ws" -ForegroundColor Cyan
        Write-Host ""
        Write-Host "Next steps:" -ForegroundColor Yellow
        Write-Host "1. Visit your repository in GitHub"
        Write-Host "2. Enable GitHub Actions in repository Settings"
        Write-Host "3. Go to Actions tab to see build status"
        Write-Host ""
        exit 0
    } else {
        Write-Host "✗ Failed to create repository" -ForegroundColor Red
        exit 1
    }
} else {
    # Manual method
    if (-not $GitHubUsername) {
        Write-Host "Enter your GitHub username:" -ForegroundColor Yellow
        $GitHubUsername = Read-Host
        if ([string]::IsNullOrWhiteSpace($GitHubUsername)) {
            Write-Host "✗ GitHub username is required" -ForegroundColor Red
            exit 1
        }
    }
    
    Write-Host ""
    Write-Host "╔════════════════════════════════════════════════════════════════════╗" -ForegroundColor Yellow
    Write-Host "║              Manual GitHub Repository Setup                        ║" -ForegroundColor Yellow
    Write-Host "╚════════════════════════════════════════════════════════════════════╝" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Please follow these steps:" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "1. Go to https://github.com/new" -ForegroundColor White
    Write-Host "2. Create a new repository with these settings:" -ForegroundColor White
    Write-Host "   - Repository name: rsvp_reader_ws" -ForegroundColor Gray
    Write-Host "   - Description: RSVP Reader for Wear OS" -ForegroundColor Gray
    Write-Host "   - Visibility: Public" -ForegroundColor Gray
    Write-Host "   - DO NOT initialize with files" -ForegroundColor Gray
    Write-Host ""
    Write-Host "3. Press Enter when you've created the repository..." -ForegroundColor Yellow
    $null = Read-Host
    
    Write-Host ""
    Write-Host "📤 Pushing to GitHub..." -ForegroundColor Cyan
    
    cd $projectDir
    
    # Remove existing origin if it exists
    git remote remove origin 2>$null
    
    # Add origin
    $repoUrl = "https://github.com/$GitHubUsername/rsvp_reader_ws.git"
    git remote add origin "$repoUrl"
    
    # Ensure we're on main branch
    git branch -M main 2>$null
    
    # Push to GitHub
    Write-Host "Pushing repository to GitHub..." -ForegroundColor Yellow
    git push -u origin main
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "╔════════════════════════════════════════════════════════════════════╗" -ForegroundColor Green
        Write-Host "║              ✓ Repository Pushed Successfully!                     ║" -ForegroundColor Green
        Write-Host "╚════════════════════════════════════════════════════════════════════╝" -ForegroundColor Green
        Write-Host ""
        Write-Host "Repository URL: $repoUrl" -ForegroundColor Cyan
        Write-Host ""
        Write-Host "Next steps:" -ForegroundColor Yellow
        Write-Host "1. Visit your repository: https://github.com/$GitHubUsername/rsvp_reader_ws" -ForegroundColor White
        Write-Host "2. Enable GitHub Actions in repository Settings" -ForegroundColor White
        Write-Host "3. Go to Actions tab to see build status" -ForegroundColor White
        Write-Host ""
        exit 0
    } else {
        Write-Host ""
        Write-Host "✗ Failed to push to GitHub" -ForegroundColor Red
        Write-Host "Please check your credentials and try again" -ForegroundColor Yellow
        exit 1
    }
}
